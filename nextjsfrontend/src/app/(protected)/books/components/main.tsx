"use client";
import { Book, ResultType } from "@/app/types/dto"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Loader2, SatelliteDish } from "lucide-react";
import { useSession } from "next-auth/react"
import { useState } from "react"
import { loanBook } from "@/app/(protected)/actions";

export function Main({ books }: { books: Book[] }) {
  const [searchTerm, setSearchTerm] = useState('')
  const { data: session, status } = useSession()
  const [borrowingBook, setBorrowingBook] = useState(false);
  const [dialogOpen, setDialogOpen] = useState(false);


  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    book.author.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const handleBorrow = (book: Book) => {
    setBorrowingBook(true);
    loanBook(book.id).then(res => {
      if (res.type === ResultType.Ok) {
        alert(res.data);
      }else {
        alert(res.errorMsg ? "You've already borrowed this book" : "Failed to borrow book");
      }
    }).finally(() => {
      setDialogOpen(false);
      setBorrowingBook(false);
    })
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
          <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
            <DialogTrigger asChild>
              <div
                className="p-4 border rounded-lg cursor-pointer hover:bg-gray-100"
              >
                <h2 className="text-lg font-semibold">{book.title}</h2>
                <p className="text-sm text-gray-600">{book.author}</p>
                <p className="text-sm mt-2">{book.available ? 'Available' : 'Borrowed'}</p>
              </div>
            </DialogTrigger>
            <DialogContent onInteractOutside={(e) => e.preventDefault()} onEscapeKeyDown={(e) => e.preventDefault()}>
              <DialogHeader>
                <DialogTitle>{book.title}</DialogTitle>
                <DialogDescription>{book.author}</DialogDescription>
              </DialogHeader>
              <p>{book.description}</p>
              <Button
                onClick={() => handleBorrow(book)}
                disabled={borrowingBook}
              >
                {borrowingBook ? (<>
                  <Loader2 className="animate-spin" />
                  Please wait
                </>) : (<>{book.available > 0 ? 'Borrow' : 'Not Available'}</>)}
              </Button>
            </DialogContent>
          </Dialog>
        ))}
      </div>
    </>
  )

}
