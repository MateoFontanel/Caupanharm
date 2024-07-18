import React from 'react'
import { createFileRoute } from '@tanstack/react-router'
import Bracket from '../components/Bracket'

export const Route = createFileRoute('/matches')({
  component: BracketComponent,
})

export function BracketComponent() {
  return (
    <Bracket />
  )
}