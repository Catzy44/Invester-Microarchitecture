import {EntityWithId} from "../store/entitySliceFactory.ts";

export type Article = EntityWithId & {
    id: number,
    url: string,
    title: string,
    content: string,
    timestamp: string,
    processedTimestamp?: string
}
export type MarketEvent = EntityWithId & {
    id: number;
    type: MarketEventType; // 0 - negatywny wpływ, 1 - pozytywny
    impactPrc: number; // 0–10
    impactChance: number; // 0–10
    startTimestamp: string; // albo string jeśli nie parsujesz od razu
    endTimestamp: string;
    scream: string;
    article?: Article;
}
export enum MarketEventType {
    Negatywny = 0,
    Pozytywny = 1,
}