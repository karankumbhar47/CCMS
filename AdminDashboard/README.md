# Admin Dashboard

A Dashboard for Admins and moderators to manage complaints.

## Development

Install project dependencies with `pnpm install`

Generate the api definations by running `pnpm run updateapi` or `pnpm run updateapi-node`

Run the development version with `pnpm run dev`

## Building

Build the project using `pnpm run build`

## Updating API definations

-   Once the definations in `api` folder are updated, run one of the following commands to update the API:
    -   If you have `openapi-generator-cli` in your $PATH, `pnpm run updateapi`
    -   If you don't have `openapi-generator-cli` in your $PATH, `pnpm run updateapi`
