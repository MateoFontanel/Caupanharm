export function getUsername(name: string): string {
    return name.split('#')[0];
}

export function getUsertag(name: string): string {
    return name.split('#')[1];
}

export function formatPlayerGamesStats(wins: number, losses: number): string {
    return `${wins + losses} partie${wins + losses >= 0 ? 's' : ''} jouée${
        wins + losses >= 0 ? 's' : ''
    } (${wins}V - ${losses}D)`;
}

export function getAgentCardWide(agent: string): string {
    return `src/assets/images/agents/VALORANT${agent}Card_wide.png`;
}