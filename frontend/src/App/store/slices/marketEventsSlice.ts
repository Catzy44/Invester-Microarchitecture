import { MarketEvent } from '../../App/App.types.tsx'
import { createEntitySlice } from '../entitySliceFactory.ts'

const marketEventsSlice = createEntitySlice<MarketEvent>('marketEvents')

export const {
    mergeMany: mergeMarketEvents,
    mergeOne: mergeMarketEvent,
    addOne: addMarketEvent,
    removeOne: removeMarketEvent,
    clearAll: clearMarketEvents,
} = marketEventsSlice.actions

export default marketEventsSlice.reducer
