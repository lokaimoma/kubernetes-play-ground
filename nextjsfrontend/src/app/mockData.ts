export interface Book {
  id: string;
  title: string;
  author: string;
  description: string;
  available: boolean;
}

export interface BorrowRecord {
  id: string;
  bookId: string;
  bookTitle: string;
  borrower: string;
  borrowDate: string;
  returnDate: string | null;
}

export const books: Book[] = [
  {
    id: '1',
    title: 'To Kill a Mockingbird',
    author: 'Harper Lee',
    description: 'A classic novel about racial injustice in the American South.',
    available: false,
  },
  {
    id: '2',
    title: '1984',
    author: 'George Orwell',
    description: 'A dystopian novel set in a totalitarian society.',
    available: false,
  },
  {
    id: '3',
    title: 'Pride and Prejudice',
    author: 'Jane Austen',
    description: 'A romantic novel of manners set in Georgian England.',
    available: true,
  },
  // Add more books as needed
];

export const borrowRecords: BorrowRecord[] = [
  {
    id: '1',
    bookId: '2',
    bookTitle: '1984',
    borrower: 'John Doe',
    borrowDate: '2023-05-15',
    returnDate: null,
  },
  {
    id: '2',
    bookId: '1',
    bookTitle: 'To Kill a Mockingbird',
    borrower: 'Jane Smith',
    borrowDate: '2023-06-01',
    returnDate: null,
  },
  // Add more borrow records as needed
];


