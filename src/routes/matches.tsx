import React from 'react'
import { createFileRoute } from '@tanstack/react-router'
import Matches from '../components/Matches'

export const Route = createFileRoute('/matches')({
  component: MatchesComponent,
})

export function MatchesComponent() {
  return (
    <Matches />
  )
}