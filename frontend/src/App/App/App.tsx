import {useEffect} from "react";
import {fet} from "./Utils.tsx";
import {mergeArticles} from "../store/slices/articlesSlice.ts";
import {filterSlice, store} from "../store/store.ts";
import {mergeArticleFilter} from "../store/slices/articlesFiltersSlice.ts";
import { Article } from "./App.types.tsx";
import { useSelector } from "react-redux"
import { BrowserRouter, Route, Routes } from "react-router";
import {NavigationPanelEl} from "../NavigationPanel/NavigationPanel.tsx";
import {NewsTable} from "../NewsTable/NewsTable.tsx";
import {mergeMarketEvents} from "../store/slices/marketEventsSlice.ts";
import s from "./App.module.scss"


function App() {

    useEffect(() => {
        const loadArticles = () => fet(`articles`).then(res=>{
            store.dispatch(mergeArticles(res))
        })

        let articlesChid = -1
        const articlesChidChecker = setInterval(()=>{
            fet(`articles/chid`).then(chid =>{
                if(chid != articlesChid || articlesChid == -1) {
                    loadArticles()
                    articlesChid = chid
                }
            })
        },2000)


        const loadMarketEvents = () => fet(`marketEvents`).then(res=>{
            store.dispatch(mergeMarketEvents(res))
        })

        let marketEventsChid = -1
        const marketEventsChidChecker = setInterval(()=>{
            fet(`articles/chid`).then(chid =>{
                if(chid != marketEventsChid || marketEventsChid == -1) {
                    loadMarketEvents()
                    marketEventsChid = chid
                }
            })
        },2000)


        return () => {
            clearInterval(articlesChidChecker)
            clearInterval(marketEventsChidChecker)
        }
    }, [])



    return <div className={`${s.background} flex flex-row wfull justify-center bg-slate-500 pattern-cross pattern pattern-bg-white pattern-size-6 pattern-opacity-20`}>
        <div className={`flex flex-row gap-2`}>

            <NavigationPanelEl/>
            <div>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<NewsTable/>}/>
                        <Route path="/p/dashboard" element={<NewsTable/>}/>
                        <Route path="/p/articles" element={<NewsTable/>}/>
                        <Route path="/p/graphs" element={<NewsTable/>}/>
                        <Route path="/*" element={<NewsTable/>}/>
                    </Routes>
                </BrowserRouter>
            </div>
        </div>
    </div>

    // const [settings,setSettings] = useState({
    //     maxArticleAge: {
    //         enabled: (getCookie("maxArticleAge.enabled") ?? "true") == "true",
    //         value: parseInt(getCookie("maxArticleAge.value") ?? "24"),
    //     },
    //     minArticleChance: {
    //         enabled: (getCookie("minArticleChance.enabled") ?? "true") == "true",
    //         value: parseInt(getCookie("minArticleChance.value") ?? "85"),
    //     },
    //     minArticleInfluence: {
    //         enabled: (getCookie("minArticleInfluence.enabled") ?? "false") == "true",
    //         value: parseInt(getCookie("minArticleInfluence.value") ?? "0"),
    //     }
    // })
    // useEffect(() => {
    //     setCookie("maxArticleAge.enabled", settings.maxArticleAge.enabled ? "true" : "false", 365)
    //     setCookie("maxArticleAge.value", settings.maxArticleAge.value, 365)
    // }, [settings])
    //
    // const pass = {settings, setSettings}
    //
    // useEffect(()=>{
    //     setTimeout(()=>{
    //         window.location.href = window.location.href
    //     },1000 * 60 * 60)
    // },[])
    //
    // return <div className={s.main}>
    //     <div>
    //         <Filters {...pass}/>
    //         <NewsTable {...pass}/>
    //     </div>
    //     <div>
    //         <ProcStats/>
    //         <MarketInfluenceSummary {...pass}/>
    //         <MarketIncluenceChart {...pass}/>
    //     </div>
    // </div>
}

export default App