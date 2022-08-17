bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

;Given the words A, B and C, obtain the word D as the sum of the integers represented by:
;the bits 1-5 of A
;the bits 6-10 of B
;the bits 11-15 of C 
                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 0000000000110110b
    b dw 0000010111000000b
    c dw 1110100000000000b
    d dw 0
; our code starts here
segment code use32 class=code
    start:
        mov bx, 0
        
        mov ax,[a]
        and ax, 0000000000111110b
        mov cl, 1
        ror ax,cl
        or bx,ax
        
        mov dx,0 
        mov ax,[b]
        and ax, 0000011111000000b
        mov cl,6
        ror ax,cl
        or dx,ax
        
        adc bx,dx
        
        mov dx,0 
        mov ax,[c]
        and ax, 1111100000000000b
        mov cl,11
        ror ax,cl
        or dx,ax
        
        adc bx,dx
        
        mov [d],bx
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
