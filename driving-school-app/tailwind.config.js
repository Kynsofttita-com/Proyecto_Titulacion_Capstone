/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          dark: '#1a2735',
          light: '#e8d8cb',
          accent: '#b87333',
        },
        brand: {
          navy: '#1a2735',
          cream: '#e8d8cb',
          copper: '#b87333',
        }
      },
      backgroundImage: {
        'gradient-primary': 'linear-gradient(135deg, #1a2735 0%, #2d3f52 100%)',
        'gradient-accent': 'linear-gradient(135deg, #b87333 0%, #8b5a2b 100%)',
      }
    },
  },
  plugins: [],
}
