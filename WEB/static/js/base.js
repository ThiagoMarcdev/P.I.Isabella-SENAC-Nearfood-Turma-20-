//  arquivo global //



//MUDANDO DE THEMA DARK E LIGHT
const storageKey = 'theme-preference';
const themeToggle = document.getElementById('theme-toggle');

const getColorPreference = () => {
  if (localStorage.getItem(storageKey)) {
    return localStorage.getItem(storageKey);
  } else {
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }
};

const setPreference = (theme) => {
  localStorage.setItem(storageKey, theme);
  document.documentElement.setAttribute('data-theme', theme);
  // Optional: Update the toggle button's appearance based on the theme
};

const onClick = () => {
  const currentTheme = document.documentElement.getAttribute('data-theme');
  const newTheme = currentTheme === 'light' ? 'dark' : 'light';
  setPreference(newTheme);
};

// Initial setup on page load
setPreference(getColorPreference());
themeToggle.addEventListener('click', onClick);


//===============================