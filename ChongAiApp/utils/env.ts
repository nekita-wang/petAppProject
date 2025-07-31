
const _Env = import.meta.env;
export const Env:ImportMetaEnv = {
    VITE_USER_NODE_ENV: _Env.VITE_USER_NODE_ENV,
    VITE_API_BASE_URL: _Env.VITE_API_BASE_URL
}