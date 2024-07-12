import React from 'react'
import { createRootRoute } from '@tanstack/react-router'

import NavBar from '../components/NavBar.tsx'


export const Route = createRootRoute({
  component: RootComponent
})

function RootComponent() {


  return (
    <>
      <NavBar/>
    </>
  )
}