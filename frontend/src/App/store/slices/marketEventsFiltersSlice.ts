import { createEntitySlice, EntityFilter } from '../entitySliceFactory.ts'
import {MarketEvent} from "./marketEventsSlice.ts"

const marketEventsFiltersSlice = createEntitySlice<EntityFilter<MarketEvent>>('marketEventsFilters')

export const {
    mergeMany: mergeMarketEventsFilters,
    mergeOne: mergeMarketEventsFilter,
    addOne: addMarketEventsFilter,
    removeOne: removeMarketEventsFilter,
    clearAll: clearMarketEventsFilters,
} = marketEventsFiltersSlice.actions

export default marketEventsFiltersSlice.reducer
