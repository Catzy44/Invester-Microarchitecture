import s from "./MarketInfluenceChart.module.scss"
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import {Line } from "react-chartjs-2";
import {useEffect, useMemo, useState} from "react";
import {MarketEvent, MarketEventType} from "../App/App.types.tsx";
import {fet} from "../App/Utils.tsx";
import {parseDate} from "../App/App.Utils.tsx";
import {calculateAvg} from "../MarketInfluenceSummary/Summary.Utils.tsx";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)

ChartJS.defaults.color = '#fff';

export const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top' as const,
        },
        title: {
            display: true,
            text: 'Wpływ wiadomości ze świata na zachowanie rynku (% wartości aktywa)',
        },
    },
}
export const options2 = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top' as const,
        },
        title: {
            display: true,
            text: 'Zachowanie rynku (wartości powyżej poziomej lini oznaczają możliwe wzrosty)',
        },
    },
}


export function MarketIncluenceChart({settings}) {
    const [dumbState,setDumbState] = useState<number>(0)
    useEffect(()=>{
        const x = setInterval(()=>{
            setDumbState(s=>s+1)
        },1000*60*3)
        return ()=>{
            clearInterval(x)
        }
    },[])

    const [events,setEvents] = useState<Array<MarketEvent>>([])
    useEffect(() => {
        fet(`marketEvents`).then(res=>{
            setEvents(res.map((marketEvent: MarketEvent)=>{
                return {
                    ...marketEvent,
                    startTimestamp: parseDate(marketEvent.startTimestamp),
                    endTimestamp: parseDate(marketEvent.endTimestamp)
                }
            }))
        })
    },[dumbState])

    const eventsFiltered = useMemo(()=>{
        let filtered = events.filter(event=>{
            if(!settings.maxArticleAge.enabled) return true

            const maxAllowedAgeMillis = settings.maxArticleAge.value * 60 * 60 * 1000
            const articleAgeMillis = event.startTimestamp.getTime()
            const currentTimeMillis = new Date().getTime()

            return articleAgeMillis > currentTimeMillis - maxAllowedAgeMillis

        })
        filtered = filtered.filter(event=>{
            if(!settings.minArticleChance.enabled) return true
            return event.impactChance >= settings.minArticleChance.value
        })
        filtered = filtered.filter(event=>{
            if(!settings.minArticleInfluence.enabled) return true
            return event.impactPrc >= settings.minArticleInfluence.value
        })
        return filtered
    }, [events, settings])

    const labels = []
    const data = {
        labels,
        datasets: [
            {
                label: 'Pozytywny',
                data: [],
                borderColor: 'rgb(0,255,15)',
                backgroundColor: 'rgba(9,136,0,0.5)',
            },
            {
                label: 'Negatywny',
                data: [],
                borderColor: 'rgb(255,0,54)',
                backgroundColor: 'rgba(162,0,34,0.5)',
            },
            {
                label: 'Rzetelność informacji',
                data: [],
                borderColor: 'rgb(0,255,228)',
                backgroundColor: 'rgb(0,204,204)',
            }
        ],
    }
    const data2 = {
        labels,
        datasets: [
            {
                label: '% zmiany wartości aktywa',
                data: [],
                borderColor: 'rgb(124,0,255)',
                backgroundColor: 'rgb(82,0,108)',
            },
            {
                label: 'Rzetelność informacji',
                data: [],
                borderColor: 'rgb(0,255,197)',
                backgroundColor: 'rgb(0,255,213)',
            },
            {
                label: '',
                data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
                borderColor: 'rgba(255,255,255,0.15)',
                backgroundColor: 'rgba(96,96,96,0.15)',
            },
        ],
    }

    const current = new Date()
    for(let i = 0; i < 30; i++) {
        labels.push("T+"+i)

        const date = new Date(current.getTime() + (i* 1000*60*60*24))
        const activeEvents = eventsFiltered.filter(ev=>ev.endTimestamp > date)

        const pos = calculateAvg(activeEvents.filter(ev=> ev.type == MarketEventType.Pozytywny))
        const neg = calculateAvg(activeEvents.filter(ev=> ev.type == MarketEventType.Negatywny))

        const dif = pos-neg

        data.datasets[0].data.push(pos)
        data.datasets[1].data.push(neg)
        data2.datasets[0].data.push(dif)
    }

    const dataSet1Max = Math.max(Math.max(...data.datasets[0].data),Math.max(...data.datasets[1].data))
    const dataSet2Max = Math.max(...data2.datasets[0].data)

    const currentActiveEventCount = eventsFiltered.filter(ev=>ev.endTimestamp.getTime() > new Date().getTime()).length

    for(let i = 0; i < 30; i++) {
        const date = new Date(current.getTime() + (i* 1000*60*60*24))
        const activeEvents = eventsFiltered.filter(ev=>ev.endTimestamp > date).length

        const r = activeEvents/currentActiveEventCount//1.111

        data.datasets[2].data.push(dataSet1Max*r)
        data2.datasets[1].data.push(dataSet2Max*r)
    }


    return <div className={s.main}>
        <Line data={data} options={options} />
        <Line data={data2} options={options2} />
    </div>
}