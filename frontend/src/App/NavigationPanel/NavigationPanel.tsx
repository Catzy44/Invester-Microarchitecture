import {ReactNode} from "react";

export function NavigationPanelEl(props): ReactNode {
    return <div className={`color bg-gray-800 min-w-3xs`}>
        <Section name={"Filtrowanie"}>
            <Option>Całe Artykuły</Option>
            <Option>Poszczególne wydarzenia</Option>
        </Section>
        <Section name={"Wyświetl dane"}>
            <Option>Tabele</Option>
            <Option>Wykresy</Option>
        </Section>
        <Section name={"Trading algorytmiczny"}>
            <Option>Zarządzaj</Option>
            <Option>Podgląd</Option>
        </Section>
        <Section name={"System"}>
            <Option>Scraping</Option>
            <Option>Processing</Option>
            <Option>Mastering</Option>
        </Section>
    </div>
}

function Section(props): ReactNode {
    return <div className={`flex flex-col`}>
        <div className={`grow px-3 bg-gray-900`}>{props.name}</div>
        {props.children}
    </div>
}

function Option(props): ReactNode{
    return <div className={`p-3`}>{props.children}</div>
}