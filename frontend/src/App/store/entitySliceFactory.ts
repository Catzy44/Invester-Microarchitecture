import { createSlice, Draft, PayloadAction } from '@reduxjs/toolkit'
import merge from 'lodash.merge'
import {FilterFn} from "./slices/articlesFiltersSlice.ts";

export type EntityWithId = { id: number }
export type ListState<T extends EntityWithId> = { list: T[] }
export type EntityFilter<T> = EntityWithId & {
    id: number
    filter: FilterFn<T>
}

export function createEntitySlice<T extends EntityWithId>(name: string) {
    const initialState: ListState<T> = { list: [] }

    const slice = createSlice({
        name,
        initialState,
        reducers: {
            mergeMany: (state, action: PayloadAction<T[]>) => {
                const incoming = action.payload

                state.list = state.list.map(e => {
                    const found = incoming.find(x => x.id === e.id)
                    return found ? merge({}, e, found) : e
                })

                const newOnes = incoming.filter(x => !state.list.some(e => e.id === x.id))
                state.list.push(...(newOnes as Draft<T>[]))
            },

            mergeOne: (state, action: PayloadAction<T>) => {
                const entity = action.payload
                let replaced = false
                state.list = state.list.map(e => {
                    if (e.id !== entity.id) return e
                    replaced = true
                    return merge({}, e, entity)
                })
                if (!replaced) state.list.push(entity as Draft<T>)
            },

            addOne: (state, action: PayloadAction<T>) => {
                state.list.push(action.payload as Draft<T>)
            },

            removeOne: (state, action: PayloadAction<number>) => {
                state.list = state.list.filter(e => e.id !== action.payload)
            },

            clearAll: (state) => {
                state.list = []
            },

            selectFiltered: (state) => {
                 state.list
            }
        },
    })

    return slice
}