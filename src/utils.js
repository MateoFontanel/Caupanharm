export function getUsername(name){
    return name.split('#')[0]
}

export function getUsertag(name){
    return name.split('#')[1]
}

export function formatPlayerGamesStats(wins, losses){
    return `${wins+losses} partie${wins+losses >= 0 ? 's' : ''} jouée${wins+losses >= 0 ? 's' : ''} (${wins}V - ${losses}D)`
}

export function getAgentCardWide(agent){
    return `src/assets/images/agents/VALORANT${agent}Card_wide.png`
}