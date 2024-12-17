export type {Book, Result, LoginResponse, BookLoan, BookloandAdminRecord};
export {ResultType, Error};


interface LoginResponse {
  access_token: string;
  expires_in: number;
  resfresh_expires_in: number;
  refresh_token: number;
  token_type: string;
  id_token?: string;
  "not-before-policy": number;
  session_state: string;
  scope: string;
  error?: string;
  error_description?: string;
  error_uri?: string; 
}

enum ResultType {
  ERROR,
  Ok,
}

enum Error {
  UN_AUTHORIZED,
  BACKEND_ERROR,
  JSON_PARSE_ERROR,
  UN_KNOWN,
}

interface Result<T> {
  type: ResultType;
  data?: T;
  error?: Error;
  errorMsg?: string;
}

interface Book {
  title: string;
  author: string;
  description: string;
  id: string;
  available: number;
}

interface BookLoan {
  id: string;
  checkoutDate: string;
  returnDate?: string;
  book: {
    id: string;
    title: string;
    description: string;
    author: string;
    stock: number;
    available: number;
  }
}

interface BookloandAdminRecord extends BookLoan {
  userEmail: string;
}

