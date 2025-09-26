import {EntityFilter} from "../store/entitySliceFactory.ts";

export type FilterSettingProps<T> = {
    id: number
    label: string
    unit: string
    selector: (state: any) => EntityFilter<T>[]
    mergeAction: (payload: EntityFilter<T>) => any
    removeAction: (id: number) => any
    predicate: (value: number) => (item: T) => boolean
}