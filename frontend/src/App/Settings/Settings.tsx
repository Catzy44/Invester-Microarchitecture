import { useSelector } from "react-redux";
import s from "./Settings.module.scss";
import {useEffect, useState } from "react";
import {mergeArticleFilter, removeArticleFilter} from "../store/slices/articlesFiltersSlice.ts";
import { store } from "../store/store";
import {mergeMarketEventsFilter, removeMarketEventsFilter} from "../store/slices/marketEventsFiltersSlice.ts";
import {Article, MarketEvent} from "../App/App.types.tsx";
import { EntityFilter } from "../store/entitySliceFactory.ts";

export function Settings(props) {
    const marketEventsFilters: EntityFilter<MarketEvent>[] = useSelector((state:any) => state.marketEventsFilters.list)
    const articlesFilters: EntityFilter<Article>[] = useSelector((state:any) => state.articlesFilters.list)

    const [maxArticleAgeEnabled, setMaxArticleAgeEnabled] = useState(articlesFilters.some(filter => filter.id == 0))
    const [maxArticleAge, setMaxArticleAge] = useState(0)
    useEffect(() => {
        const id = 0
        if(!maxArticleAgeEnabled) {
            store.dispatch(removeArticleFilter(id))
        }
        store.dispatch(mergeArticleFilter({id: id, filter: (item) => {
            return new Date(item.timestamp).getTime() > Date.now() - 1000 * 60 * 60 * maxArticleAge
        }}))

    },[maxArticleAge,maxArticleAgeEnabled])

    const [minArticleChanceEnabled, setMinArticleChanceEnabled] = useState(marketEventsFilters.some(filter => filter.id == 1))
    const [minArticleChance, setMinArticleChance] = useState(0)
    useEffect(() => {
        const id = 1
        if(!minArticleChance) {
            store.dispatch(removeMarketEventsFilter(id))
        }
        store.dispatch(mergeMarketEventsFilter({id: id, filter: (item: MarketEvent) => {
            return item.impactChance >= minArticleChance
        }}))

    },[minArticleChance,minArticleChanceEnabled])

    const [minArticleInfluenceEnabled, setMinArticleInfluenceEnabled] = useState(marketEventsFilters.some(filter => filter.id == 2))
    const [minArticleInfluence, setMinArticleInfluence] = useState(0)
    useEffect(() => {
        const id = 2
        if(!minArticleInfluence) {
            store.dispatch(removeMarketEventsFilter(id))
        }
        store.dispatch(mergeMarketEventsFilter({id: id, filter: (item: MarketEvent) => {
            return item.impactPrc >= minArticleInfluence
        }}))

    },[minArticleInfluence,minArticleInfluenceEnabled])

    return <div className={s.applicableEventsSelector}>
        <div>
            <input type="checkbox" checked={maxArticleAgeEnabled} onChange={ev=>setMaxArticleAgeEnabled(ev.target.checked)}/>
            <span>max wiek</span>
            <input type="number" value={maxArticleAge} onChange={ev => setMaxArticleAge(parseInt(ev.target.value))}/>
            <span>godziny</span>
        </div>
        <div>
            <input type="checkbox" checked={minArticleChanceEnabled} onChange={ev=>setMinArticleChanceEnabled(ev.target.checked)}/>
            <span>min celność</span>
            <input type="number" value={minArticleChance} onChange={ev => setMinArticleChance(parseInt(ev.target.value))}/>
            <span>%</span>
        </div>
        <div>
            <input type="checkbox" checked={minArticleInfluenceEnabled} onChange={ev=>setMinArticleInfluenceEnabled(ev.target.checked)}/>
            <span>min wpływ na rynek</span>
            <input type="number" value={minArticleInfluence} onChange={ev => setMinArticleInfluence(parseInt(ev.target.value))}/>
            <span>%</span>
        </div>
    </div>
}