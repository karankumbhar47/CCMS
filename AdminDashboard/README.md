# Admin Dashboard

A Dashboard for Admins and moderators to manage complaints.

## Development

Install project dependencies with `pnpm install`

Generate the api definations by running `pnpm run updateapi` or `pnpm run updateapi-node`

Generate the material-ui stylesheet by running `pnpm run prepare-theme`.

Run the development version with `pnpm run dev`

## Building

Build the project using `pnpm run build`

## Updating API definations

-   Once the definations in `api` folder are updated, run one of the following commands to update the API:
    -   If you have `openapi-generator-cli` in your $PATH, `pnpm run updateapi`
    -   If you don't have `openapi-generator-cli` in your $PATH, `pnpm run updateapi`

## Changing theme

- To change the theme, first update `src/theme` and then run `pnpm run prepare-theme`
