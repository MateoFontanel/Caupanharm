import React from "react";
import { createRootRoute } from "@tanstack/react-router";
import {
  QueryClientProvider,
  QueryClient,
} from "@tanstack/react-query";

import NavBar from "../components/NavBar.tsx";

export const Route = createRootRoute({
  component: RootComponent,
});

function RootComponent() {
  const queryClient = new QueryClient()

  return (
    <QueryClientProvider client={queryClient}>
      <NavBar />
    </QueryClientProvider>
  );
}
