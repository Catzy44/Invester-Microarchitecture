import { useSelector } from "react-redux";
import s from "./Filters.module.scss";
import {useEffect, useState } from "react";
import {mergeArticleFilter, removeArticleFilter} from "../store/slices/articlesFiltersSlice.ts";
import { store } from "../store/store";
import {mergeMarketEventsFilter, removeMarketEventsFilter} from "../store/slices/marketEventsFiltersSlice.ts";
import {Article, MarketEvent} from "../App/App.types.tsx";
import { FilterSettingProps } from "./Filters.types.tsx";

function FilterSetting<T>({id, label, unit, selector, mergeAction, removeAction, predicate, }: FilterSettingProps<T>) {
    const filters = useSelector(selector)
    const [enabled, setEnabled] = useState(filters.some(f => f.id === id))
    const [value, setValue] = useState(0)

    useEffect(() => {
        if (!enabled) {
            store.dispatch(removeAction(id))
            return
        }
        store.dispatch(mergeAction({ id, filter: predicate(value) }))
    }, [enabled, value])

    return (
        <div>
            <input type="checkbox" checked={enabled} onChange={ev => setEnabled(ev.target.checked)}/>
            <span>{label}</span>
            <input type="number" value={value} onChange={ev => setValue(parseInt(ev.target.value) || 0)}/>
            <span>{unit}</span>
        </div>
    )
}

export function Filters() {
    return (
        <div className={s.applicableEventsSelector}>
            <FilterSetting<Article>
                id={0}
                label="max wiek"
                unit="godziny"
                selector={state => state.articlesFilters.list}
                mergeAction={mergeArticleFilter}
                removeAction={removeArticleFilter}
                predicate={val => {
                    return function(item: Article) {
                        const itemTime = new Date(item.timestamp).getTime()
                        const limit = Date.now() - 1000 * 60 * 60 * val
                        return itemTime > limit
                    }
                }}
            />

            <FilterSetting<MarketEvent>
                id={1}
                label="min celność"
                unit="%"
                selector={state => state.marketEventsFilters.list}
                mergeAction={mergeMarketEventsFilter}
                removeAction={removeMarketEventsFilter}
                predicate={val => {
                    return (item: MarketEvent) => {
                        return item.impactChance >= val
                    }
                }}
            />

            <FilterSetting<MarketEvent>
                id={2}
                label="min wpływ na rynek"
                unit="%"
                selector={state => state.marketEventsFilters.list}
                mergeAction={mergeMarketEventsFilter}
                removeAction={removeMarketEventsFilter}
                predicate={val => {
                    return (item: MarketEvent) => {
                        return item.impactPrc >= val
                    }
                }}
            />
        </div>
    )
}