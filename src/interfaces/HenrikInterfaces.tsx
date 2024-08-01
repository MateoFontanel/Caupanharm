export interface HenrikErrorsInterface{
  data: HenrikErrorInterface[]
}

export interface HenrikErrorInterface {
  code: number;
  message: string;
  status: number;
  details: string | undefined;
}

export interface FullMatchesAccordionProps {
  player: string;
  page: number
  data: V1LifetimeMatches;
}

export interface AccordionMatchElementProps {
  id: number;
  data: V1LifetimeMatchItem;
  expanded: string | false;
  onChange: (panel: string) => (event: React.SyntheticEvent, newExpanded: boolean) => void;
}

export interface MatchV4 {
  status: number;
  data: MatchV4Data;
}

export interface V1LifetimeMatches {
  status: number;
  name: string;
  tag: string;
  results: {
    total: number;
    returned: number;
    before: number;
    after: number;
  };
  data: V1LifetimeMatchItem[]
}

export interface V1LifetimeMatchItem{
  meta: V1LifetimeMatchItemMetadata
  stats: V1LifetimeMatchItemStats
  teams: V1LifetimeMatchItemTeams
}

export interface MatchV4Data {
  metadata: MetadataFull;
  players: Player[];
  observers: Observer[];
  coaches: Coach[];
  teams: Team[];
  rounds: Round[];
  kills: KillEvents[];
}

interface V1LifetimeMatchItemMetadata{
  id: string
  map: Map
  version: string
  mode: string
  started_at: string
  season: Season
  region: string | undefined
  cluster: string | undefined
}

interface V1LifetimeMatchItemStats{
  puuid: string
  team: string
  level: number
  character: Character
  tier: number
  score: number
  kills: number
  deaths: number
  assists: number
  shots: Shots
  damage: Damage
}

interface V1LifetimeMatchItemTeams{
  red: number | undefined
  blue: number | undefined
}

interface Shots{
  head: number
  body: number
  leg: number
}

interface Damage{
  dealt: number
  received: number
}

interface Character{
  id: string
  name: string | undefined
}

interface MetadataFull {
  match_id: string;
  map: Map;
  game_version: string;
  game_length_in_ms: number;
  started_at: string;
  is_completed: boolean;
  queue: Queue;
  season: Season;
  platform: string;
  premier_info?: PremierInfo;
  party_rr_penaltys?: Penalty[];
  region: string;
  cluster?: string;
}

interface Map {
  id: string;
  name: string;
}

interface Queue {
  id: string;
  name?: string;
  mode_type?: string;
}

interface Season {
  id: string;
  short: string | undefined;
}

interface Penalty {
  party_id: string;
  penalty: number;
}

interface PremierInfo {
  tournament_id?: string;
  matchup_id?: string;
}

interface Player {
  puuid: string;
  name: string;
  tag: string;
  team_id: string;
  platform: string;
  party_id: string;
  agent: Agent;
  stats: Stats;
  ability_casts: Abilities;
  tier: Tier;
  card_id?: string;
  title_id?: string;
  prefered_level_border?: string;
  account_level: number;
  session_playtime_in_ms: number;
  behavior: Behavior;
  economy: EconomyShort;
}

interface Tier {
  id: string;
  name?: string;
}

interface Agent {
  id: string;
  name: string;
}

interface Behavior {
  afk_rounds: number;
  friendly_fire: FriendlyFire;
  rounds_in_spawn: number;
}

interface FriendlyFire {
  incoming: number;
  outgoing: number;
}

interface Stats {
  score: number;
  kills: number;
  deaths?: number;
  assists: number;
  bodyshots: number;
  headshots: number;
  legshots: number;
  damage?: Damage;
}

interface Damage {
  total: number;
  perRound: number;
}

interface EconomyShort {
  spent: EconomyDetails;
  loadout_value: EconomyDetails;
}

interface Abilities {
  grenade: number;
  ability_1: number;
  ability_2: number;
  ultimate: number;
}

interface EconomyDetails {
  overall: number;
  average: number;
}

interface Observer {
  puuid: string;
  name: string;
  tag: string;
  account_level: number;
  session_playtime_in_ms: number;
  card_id: string;
  title_id: string;
  party_id: string;
}

interface Coach {
  puuid: string;
  team: string;
}

export interface Team {
  team_id: string;
  rounds: RoundsShort;
  won: boolean;
  premier_roster?: Roster;
}

interface RoundsShort {
  won: number;
  lost: number;
}

interface Roster {
  id: string;
  members: string[];
  name: string;
  customization: Customization;
  tag: string;
}

interface Customization {
  icon: string;
  image: string;
  primary_color: string;
  secondary_color: string;
  tertiary_color: string;
}

interface Round {
  id: string;
  result: string;
  ceremony: string;
  winning_team: string;
  plant?: PlantEvents;
  defuse?: DefuseEvents;
  stats: PlayerStats[];
}

interface PlantEvents {
  round_time_in_ms: number;
  site: string;
  location?: Location;
  player: PlayerShort;
  players_locations?: PlayerLocation[];
}

interface DefuseEvents {
  round_time_in_ms: number;
  location?: Location;
  player: PlayerShort;
  players_locations?: PlayerLocation[];
}

interface PlayerShort {
  puuid: string;
  name: string;
  tag: string;
  team: string;
}

interface PlayerLocation {
  player: PlayerShort;
  view_radians: number;
  location?: Location;
}

interface Location {
  x: number;
  y: number;
}

interface PlayerStats {
  ability_casts: Abilities;
  player: PlayerShort;
  damage_events: DamageEvents[];
  stats: Stats;
  economy: Economy;
  was_afk: boolean;
  received_penalty: boolean;
  stayed_in_spawn: boolean;
}

interface DamageEvents {
  player: PlayerShort;
  bodyshots: number;
  damage: number;
  headshots: number;
  legshots: number;
}

interface Economy {
  loadout_value: number;
  weapon?: Weapon;
  armor?: Armor;
  remaining: number;
}

interface Armor {
  id: string;
  name: string;
}

interface KillEvents {
  round: number;
  time_in_round_in_ms: number;
  time_in_match_in_ms: number;
  killer: PlayerShort;
  victim: PlayerShort;
  assistants: PlayerShort[];
  location?: Location;
  weapon: Weapon;
  secondary_fire_mode: boolean;
  player_locations: PlayerLocation[];
}

interface Weapon {
  id: string;
  name?: string;
  type?: string;
}
