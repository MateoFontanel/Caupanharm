interface BracketPlayerInterface {
  id: string;
  resultText: string;
  isWinner: boolean;
  status: "PLAYED" | "NO_SHOW" | "WALK_OVER" | "NO_PARTY" | null;
  name: string;
}

export interface BracketMatchInterface {
  id: string;
  nextMatchId: string;
  tournamentRoundText: string;
  state: "NO_SHOW" | "WALK_OVER" | "NO_PARTY" | "DONE" | "SCORE_DONE";
  participants: BracketPlayerInterface[];
}

export interface AdditionalPlayerDataInterface {
  name: string;
  stats: {
    rank: string;
    favoriteAgents: string[];
    kills: number;
    deaths: number;
    assists: number;
    kast: number;
    kd: number;
    acs: number;
    killsPerRound: number;
    wins: number;
    losses: number;
  };
}

export interface DemoMatchesInterface {
  playerId: number;
  data: PlayerMatchesInterface[];
}

export interface PlayerMatchesInterface {
  name: string;
  matches: DemoMatchInterface[];
}

interface DemoMatchInterface {
  date: string;
  map: string;
  result: string;
  allyScore: number;
  enemyScore: number;
}

export interface PlayerCardProps {
  id: number;
  onClick: (id: number) => void;
  width: string;
  player: {
    name: string;
    stats: {
      favoriteAgents: string[];
      wins: number;
      losses: number;
    };
  };
}

export interface ProfileProps {
    username: string
}

export interface NavButtonsProps{
  page: number,
  setPage: React.Dispatch<React.SetStateAction<number>>
  dataSize: number
  pageSize: number
}