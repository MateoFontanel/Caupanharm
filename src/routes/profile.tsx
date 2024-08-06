import React from "react";
import { createFileRoute } from "@tanstack/react-router";
import Profile from "../components/profile/Profile";
import { useLocation } from "@tanstack/react-router";

export const Route = createFileRoute("/profile")({
  component: ProfileComponent,
});

export function ProfileComponent() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const param = queryParams.get("username")! // Non-null assertion is done in NavBar.tsx so the username param always exists

    return <Profile username={decodeURIComponent(param)} />;
  
}
