const alphabet = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $*+-./:';

export function encodeBase44(value) {
  return btoa(value);
}

export function decodeBase44(value) {
  return atob(value);
}

export function sanitizeBase44(value) {
  return value.toString().trim();
}
