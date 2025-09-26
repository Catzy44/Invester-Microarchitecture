import {useEffect, useMemo, useState} from "react";
import {MarketEvent, MarketEventType} from "../App/App.types.tsx";
import {fet} from "../App/Utils.tsx";
import {parseDate} from "../App/App.Utils.tsx";
import s from "./Summary.module.scss";
import {calculateAvg} from "./Summary.Utils.tsx";

export function MarketInfluenceSummary({settings}) {
    const [dumbState,setDumbState] = useState<number>(0)
    useEffect(()=>{
        const x = setInterval(()=>{
            setDumbState(s=>s+1)
        },1000*60*3)
        return ()=>{
            clearInterval(x)
        }
    },[])

    const [events,setEvents] = useState<Array<MarketEvent>>([])
    useEffect(() => {
        fet(`marketEvents/current`).then(res=>{
            setEvents(res.map((marketEvent: MarketEvent)=>{
                return {
                    ...marketEvent,
                    startTimestamp: parseDate(marketEvent.startTimestamp),
                    endTimestamp: parseDate(marketEvent.endTimestamp)
                }
            }))
        })
    },[dumbState])

    const eventsFiltered = useMemo(()=>{
        let filtered = events.filter(event=>{
            if(!settings.maxArticleAge.enabled) return true

            const maxAllowedAgeMillis = settings.maxArticleAge.value * 60 * 60 * 1000
            const articleAgeMillis = event.startTimestamp.getTime()
            const currentTimeMillis = new Date().getTime()

            return articleAgeMillis > currentTimeMillis - maxAllowedAgeMillis

        })
        filtered = filtered.filter(event=>{
            if(!settings.minArticleChance.enabled) return true
            return event.impactChance >= settings.minArticleChance.value
        })
        filtered = filtered.filter(event=>{
            if(!settings.minArticleInfluence.enabled) return true
            return event.impactPrc >= settings.minArticleInfluence.value
        })
        return filtered
    }, [events, settings])

    const positiveInfluence = calculateAvg(eventsFiltered.filter(ev=> ev.type == MarketEventType.Pozytywny))
    const negativeInfluence = calculateAvg(eventsFiltered.filter(ev=> ev.type == MarketEventType.Negatywny))

    const influenceDiffer = positiveInfluence-negativeInfluence
    const influenceDifferFixed = influenceDiffer.toFixed(3)

    const difArrowUpOrDown = influenceDiffer > 0 ? "▲" : "▼"

    useEffect(() => {
        document.title = `Invester ${difArrowUpOrDown} ${influenceDifferFixed}%`
    }, [influenceDiffer]);

    return <div className={s.main}>
        <span>Liczba wydarzeń aktywnie wywierających wpływ na rynek: {eventsFiltered.length}</span>
        <span>Średnie wartości wpływu aktywnych wydarzeń rynkowych, ważone ich celnością</span>
        <span>Pozytywna: {positiveInfluence.toFixed(3)} %</span><br/>
        <span>Negatywna: {negativeInfluence.toFixed(3)} %</span>
        <span className={`${s.difference} ${influenceDiffer > 0 ? s.green : s.red}`}>
            Różnica: {influenceDifferFixed} % {difArrowUpOrDown}
        </span>
        {/*<table className={s.main}>*/}
        {/*    {events.map((event)=><MarketEventRow key={event.id} marketEvent={event}/>)}*/}
        {/*</table>*/}
    </div>
}