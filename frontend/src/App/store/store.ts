import { configureStore } from '@reduxjs/toolkit'
import articlesReducer from './slices/articlesSlice'
import articlesFiltersReducer from './slices/articlesFiltersSlice'
import marketEventsReducer from './slices/marketEventsSlice'
import marketEventsFiltersReducer, { EntityFilter } from './slices/marketEventsFiltersSlice'
import { EntityWithId } from './entitySliceFactory'

export const store = configureStore({
    reducer: {
        articles: articlesReducer,
        articlesFilters: articlesFiltersReducer,
        marketEvents: marketEventsReducer,
        marketEventsFilters: marketEventsFiltersReducer,
    },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export function filterSlice(entities: EntityWithId[], filters: EntityFilter<any>[]) {
    return filters.reduce((list, filter) => list.filter(filter.filter), entities)
}

/*export function selectFiltered(storeName: string) {
    const storeState = store.getState()
    const slice = storeState[storeName] as {
        list: EntityWithId[],
        filters?: FilterFn<EntityWithId>[]
    }
    const {list, filters = []} = slice
    return filters.reduce((list, filter) => list.filter(filter), list)
}*/