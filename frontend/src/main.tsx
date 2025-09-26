import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App/App/App.tsx'
import './globals.scss';
import {Provider} from "react-redux";
import { store } from './App/store/store.ts';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
        <App/>
    </Provider>
  </StrictMode>,
)
