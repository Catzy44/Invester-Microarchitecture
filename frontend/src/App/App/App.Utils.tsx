import {MarketEvent, MarketEventType} from "./App.types.tsx";

export function parseDate(date?: any) {
    return date == null ? null : new Date(date)
}
export function formatTimeLeft(now: Date, end: Date) {
    let diffMs = end.getTime() - now.getTime();
    if (diffMs < 0) diffMs = 0;

    const days    = Math.floor(diffMs / (1000 * 60 * 60 * 24));
    const hours   = Math.floor((diffMs / (1000 * 60 * 60)) % 24);
    const minutes = Math.floor((diffMs / (1000 * 60)) % 60);

    const parts: string[] = [];
    if (days)    parts.push(`${days}d`);
    if (hours)   parts.push(`${hours}h`);
    if (minutes) parts.push(`${minutes}m`);

    // jeżeli wszystko jest równe 0 → pokaż „0 m”
    return parts.length ? parts.join(' ') : 'KONIEC WPŁYWU';
}
export function formatDate(isoString : string) {

    if(isoString == null) {
        return "BRAK"
    }

    const date = new Date(isoString)
    return date.toLocaleString('pl-PL', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}


export function marketColor(marketEvent: MarketEvent) {
    const movePct = marketEvent.impactPrc
    const probPct = marketEvent.impactChance
    const direction = marketEvent.type

    const maxMove = 1.0;
    const investThreshold = 0;
    const fadeStrength = 0.6; // jak mocno blendować do szarości przy słabym sygnale

    const hueDeg = direction === MarketEventType.Pozytywny ? 120 : 0;
    const sign = hueDeg === 120 ? 1 : -1;

    let saturation = Math.min(1, Math.sqrt(movePct / maxMove)) * 0.6;
    let value = 0.2 + 0.65 * (probPct / 100);

    const endTimestamp = new Date(marketEvent.endTimestamp)
    if(new Date().getTime() > endTimestamp.getTime()) {
        saturation = saturation/2
        value = value/2
    }

    const { r, g, b } = hsvToRgb(hueDeg, saturation, value);

    // blend factor: jak mocno kolor ma się wygasić
    const strength = Math.sqrt((movePct / maxMove) * (probPct / 100));
    const fadeFactor = Math.min(1, Math.max(0, strength));
    const gray = 0.5;

    const rFaded = r * fadeFactor + gray * (1 - fadeFactor) * fadeStrength;
    const gFaded = g * fadeFactor + gray * (1 - fadeFactor) * fadeStrength;
    const bFaded = b * fadeFactor + gray * (1 - fadeFactor) * fadeStrength;

    const hexColor =
        "#" +
        [rFaded, gFaded, bFaded]
            .map(c => Math.round(c * 255).toString(16).padStart(2, "0").toUpperCase())
            .join("");

    const ev = sign * movePct * (probPct / 100);
    const shouldInvest = ev > investThreshold;

    return { hexColor, shouldInvest, ev };
}

/* ------------- Pomocnicza konwersja HSV → RGB -------------- */
function hsvToRgb(h, s, v) {
    const c = v * s;
    const x = c * (1 - Math.abs(((h / 60) % 2) - 1));
    const m = v - c;

    let r1, g1, b1;
    if (h < 60)       [r1, g1, b1] = [c,  x, 0];
    else if (h < 120) [r1, g1, b1] = [x,  c, 0];
    else if (h < 180) [r1, g1, b1] = [0,  c, x];
    else if (h < 240) [r1, g1, b1] = [0,  x, c];
    else if (h < 300) [r1, g1, b1] = [x,  0, c];
    else              [r1, g1, b1] = [c,  0, x];

    return { r: r1 + m, g: g1 + m, b: b1 + m };
}