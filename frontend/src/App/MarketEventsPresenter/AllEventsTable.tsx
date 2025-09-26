import {useEffect, useMemo, useState} from "react";
import {Article, MarketEvent, MarketEventType} from "../App/App.types.tsx";
import {fet} from "../App/Utils.tsx";
import {formatDate, formatTimeLeft, marketColor, parseDate} from "../App/App.Utils.tsx";
import s from "./AllEventsTable.module.scss";
import { starsFromNum } from "./AllEventsTable.Utils.tsx";
import {useSelector} from "react-redux";
import {filterSlice} from "../store/store.ts";

export function AllEventsTable(props) {

    const articles = useSelector((state:any) => state.articles.list)
    const articlesFilters = useSelector((state:any) => state.articlesFilters.list)
    const filtered = filterSlice(articles,articlesFilters)
    const sorted = filtered.sort((a: Article,b: Article) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())


    return <table className={s.main}>
        <tbody>
        {sorted.map((article: Article,index: number) => <ArticleRowTr index={index} key={article.id} article={article}/>)}
        </tbody>
    </table>
}

function ArticleRowTr({ article, index }: { article: Article,index: number }) {

    const events = useSelector((state:any) => state.marketEvents.list)
    const eventsThisArticle = events.filter((event: MarketEvent)=> event.article.id == article.id)

    const eventsFilters = useSelector((state:any) => state.marketEventsFilters.list)
    const filtered = filterSlice(eventsThisArticle, eventsFilters)


    if(filtered.length == 0){
        //return null
    }

    return <>
        <tr className={`bg-zinc-800`}>
            <th colSpan={5}><a href={article.url}>{article.title}</a></th>
            <td>{formatDate(article.timestamp)}</td>
            <td>Wiek: {formatTimeLeft(new Date(article.timestamp),new Date())}</td>
        </tr>
        <tr className={`${s.titleRow} bg-zinc-800`}>
            <th>celność</th>
            <th>typ</th>

            <th>wpływ na rynek</th>
            <th>tekst</th>

            <th>początek wpływu</th>
            <th>koniec wpływu</th>
            <th>czas do końca wpływu</th>
        </tr>
        {filtered.map((marketEvent: MarketEvent) =><MarketEventRow marketEvent={marketEvent} key={marketEvent.id}/>)}
        <tr className={s.spacer}></tr>
    </>
}

export function MarketEventRow({marketEvent}: {marketEvent: MarketEvent}) {

    const color = marketColor(marketEvent)

    return <tr key={marketEvent.id} className={marketEvent.type == MarketEventType.Pozytywny ? s.positive : s.negative} style={{backgroundColor:color.hexColor}}>
        <td>{starsFromNum(marketEvent.impactChance/10)}</td>
        <td>{
            marketEvent.type == MarketEventType.Pozytywny
                ? "▲"
                : "▼"
        }</td>

        <td>{marketEvent.impactPrc} %</td>
        <td>{marketEvent.scream}</td>

        <td>{formatDate(marketEvent.startTimestamp)}</td>
        <td>{formatDate(marketEvent.endTimestamp)}</td>
        <td>{formatTimeLeft(new Date(),new Date(marketEvent.endTimestamp))}</td>
    </tr>
}