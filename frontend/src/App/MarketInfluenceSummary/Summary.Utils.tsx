import { MarketEvent } from "../App/App.types.tsx";
import {WeightedAverageThing} from "./Summary.Types.tsx";

export function weightedAverage(data: WeightedAverageThing[]) {
    const weightedSum = data.reduce((sum, item) => sum + item.value * item.weight, 0);
    const totalWeight = data.reduce((sum, item) => sum + item.weight, 0);
    return weightedSum / totalWeight;
}

export function calculateAvg(events: MarketEvent[]) {
    return weightedAverage(
        events.map((ev): WeightedAverageThing => ({
            value: ev.impactPrc,
            weight: ev.impactChance
        }))
    )
}