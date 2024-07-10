module.exports = {
  parser: "@typescript-eslint/parser",
  extends: [
    "eslint:recommended",
    "plugin:react/recommended",
    "plugin:@typescript-eslint/recommended"
  ],
  plugins: ["@typescript-eslint"],
  rules: {
    // Ajoutez vos règles ESLint ici
  },
  settings: {
    react: {
      version: "detect"
    }
  }
}
