import { createEntitySlice, EntityFilter } from '../entitySliceFactory.ts'
import { Article } from './articlesSlice.ts'

export type FilterFn<T> = (item: T, index: number, array: T[]) => boolean

/*export type ArticleFilter = EntityWithId & {
    id: number
    filter: FilterFn<Article>
}*/

const articlesFiltersSlice = createEntitySlice<EntityFilter<Article>>('articlesFilters')

export const {
    mergeMany: mergeArticlesFilters,
    mergeOne: mergeArticleFilter,
    addOne: addArticleFilter,
    removeOne: removeArticleFilter,
    clearAll: clearArticlesFilters,
} = articlesFiltersSlice.actions

export default articlesFiltersSlice.reducer
