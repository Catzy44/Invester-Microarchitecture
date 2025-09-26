import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import tailwindcss from '@tailwindcss/vite'


console.log('Setting terminal title...')
process.stdout.write('\x1b]0;RAAW.ONE VITE DEV\x07')
		
// https://vite.dev/config/
export default defineConfig({
  plugins: [
    tailwindcss(),
    react()
  ],
})