export const GC_USER_ID = 'graphcool-user-id'
export const GC_BACKEND = 'http://localhost:8060'
export const GC_AUTH_TOKEN = 'graphcool-auth-token'
export const GC_LOGGED_IN = 'loggedIn'
// Keys are in seconds and values are in milliseconds
export const GC_UPDATE_TIMES = {
  '900': 1000 * 5,   // 15 mins
  '1800': 1000 * 60 * 5,  // 30 mins
  '3600': 1000 * 60 * 10, // 1 hour
};
