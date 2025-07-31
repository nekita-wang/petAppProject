
interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string;
  readonly VITE_USER_NODE_ENV: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
