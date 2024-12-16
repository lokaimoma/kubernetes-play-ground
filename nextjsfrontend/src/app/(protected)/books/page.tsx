'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { books, Book } from '@/app/mockData';
import { useSession } from 'next-auth/react'
import { redirect } from 'next/navigation'

export default function Home() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedBook, setSelectedBook] = useState<Book | null>(null)
  const { data: session, status } = useSession()


  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    book.author.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const handleBorrow = (book: Book) => {
    // Here you would typically make an API call to update the book's status
    console.log(`Borrowing book: ${book.title}`)
    // For now, we'll just close the dialog
    setSelectedBook(null)
  }

  return (
    <>
      <Input
        type="search"
        placeholder="Search books..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="mb-4"
      />
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {filteredBooks.map(book => (
          <Dialog key={book.id}>
            <DialogTrigger asChild>
              <div
                className="p-4 border rounded-lg cursor-pointer hover:bg-gray-100"
                onClick={() => setSelectedBook(book)}
              >
                <h2 className="text-lg font-semibold">{book.title}</h2>
                <p className="text-sm text-gray-600">{book.author}</p>
                <p className="text-sm mt-2">{book.available ? 'Available' : 'Borrowed'}</p>
              </div>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>{book.title}</DialogTitle>
                <DialogDescription>{book.author}</DialogDescription>
              </DialogHeader>
              <p>{book.description}</p>
              <Button
                onClick={() => handleBorrow(book)}
              >
                {book.available ? 'Borrow' : 'Not Available'}
              </Button>
            </DialogContent>
          </Dialog>
        ))}
      </div>
    </>
  )
}

