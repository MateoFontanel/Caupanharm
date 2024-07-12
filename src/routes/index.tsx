import React from "react"
import { createFileRoute } from '@tanstack/react-router'
import Home from '../components/Home'

export const Route = createFileRoute('/')({
  component: HomeComponent,
})

export function HomeComponent() {
  return (
    <Home />
  )
}