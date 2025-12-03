import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Forward all requests starting with /files, /login, /register, /logout to Spring Boot
      '/files': 'http://localhost:8080',
      '/login': 'http://localhost:8080',
      '/register': 'http://localhost:8080',
      '/logout': 'http://localhost:8080'
    }
  }
})