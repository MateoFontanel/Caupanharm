import React from 'react'
import { createFileRoute } from '@tanstack/react-router'
import Players from '../components/Players'

export const Route = createFileRoute('/players')({
  component: PlayersComponent,
})

export function PlayersComponent() {
  return (
    <Players />
  )
}