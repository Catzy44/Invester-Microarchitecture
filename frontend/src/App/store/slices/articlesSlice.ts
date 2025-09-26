import { Article } from '../../App/App.types.tsx'
import { createEntitySlice } from '../entitySliceFactory.ts'

const articlesSlice = createEntitySlice<Article>('articles')

export const {
    mergeMany: mergeArticles,
    mergeOne: mergeArticle,
    addOne: addArticle,
    removeOne: removeArticle,
    clearAll: clearArticles,
} = articlesSlice.actions

export default articlesSlice.reducer
