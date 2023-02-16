; 12. Determine the list of nodes accesed in preorder in a tree of type (2).
; cadr L  -> left subtree
; caddr L  -> right subtree
; (A (B) (C (D) (E))) -> type 2

;appendLists 
(defun appendLists (L P)
  (cond 
   ((NULL L) P)
   ((NULL P) L)
   (T (CONS (CAR L) (appendLists (CDR L) P)))
  )
)

(defun preorderTraversal (L)
  (cond
   ((NULL L) NIL)
   ((NULL (CADR L)) (LIST (CAR L)))
   ((NULL (CADDR L)) (CONS (CAR L) (preorderTraversal (CADR L))))
   (T (CONS (CAR L) (appendLists (preorderTraversal (CADR L)) (preorderTraversal (CADDR L)))))
  )
)

(print (preorderTraversal '(A (B (F)) (C (D) (E))) ))
