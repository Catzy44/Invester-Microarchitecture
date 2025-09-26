import s from "./Settings.module.scss";
import { merge } from "chart.js/helpers";

export function Settings({settings, setSettings}) {

    return <div className={s.applicableEventsSelector}>
        <div>
            <input type="checkbox" checked={settings.maxArticleAge.enabled} onChange={(ev)=>{
                setSettings(oldSettings => {
                    return {...merge(oldSettings, {maxArticleAge: {enabled: (ev.target as HTMLInputElement).checked}})}
                })
            }}/>
            <span>max wiek</span>
            <input type="number" value={settings.maxArticleAge.value} onChange={ev => {
                setSettings(oldSettings=> {
                    return {...merge(oldSettings, {maxArticleAge: {value: ev.target.value}})}
                })
            }}/>
            <span>godziny</span>
        </div>
        <div>
            <input type="checkbox" checked={settings.minArticleChance.enabled} onChange={(ev)=>{
                setSettings(oldSettings => {
                    return {...merge(oldSettings, {minArticleChance: {enabled: (ev.target as HTMLInputElement).checked}})}
                })
            }}/>
            <span>min celność</span>
            <input type="number" value={settings.minArticleChance.value} onChange={ev => {
                setSettings(oldSettings=> {
                    return {...merge(oldSettings, {minArticleChance: {value: ev.target.value}})}
                })
            }}/>
            <span>%</span>
        </div>
        <div>
            <input type="checkbox" checked={settings.minArticleInfluence.enabled} onChange={(ev)=>{
                setSettings(oldSettings => {
                    return {...merge(oldSettings, {minArticleInfluence: {enabled: (ev.target as HTMLInputElement).checked}})}
                })
            }}/>
            <span>min wpływ na rynek</span>
            <input type="number" value={settings.minArticleInfluence.value} onChange={ev => {
                setSettings(oldSettings=> {
                    return {...merge(oldSettings, {minArticleInfluence: {value: ev.target.value}})}
                })
            }}/>
            <span>%</span>
        </div>
    </div>
}