; Read from file numbers.txt a string of numbers (odd and even). Build two strings using readen numbers:
; P – only with even numbers
; N – only with odd numbers
; Display the strings on the screen.
 
bits 32 
 
global start        
 
extern exit, fopen, fscanf, printf, fclose            
import exit msvcrt.dll
import fopen msvcrt.dll  
import fscanf msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
 
%include "parity.asm"
 
segment data use32 class=data
    file_name db "numbers.txt", 0
    access_mode db "r", 0
    file_descriptor dd -1
    format db "%d", 0
    format_print db "%d ", 0
    even_message db "even numbers: ", 0
    odd_message db "odd numbers: ", 0
    new_line db 10, 0
    current dd 0
    len_P dd 0
    len_N dd 0
    P resd 10
    N resd 10
 
segment code use32 class=code
    start:
        ; fopen(char *file_name, char *access_mode)
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4 * 2
 
        ; check if there was an error
        cmp eax, 0
        je finish
 
        ; save the file descriptor
        mov [file_descriptor], eax
 
        mov esi, P ; in esi we'll have the address of P
        mov edi, N ; in edi we'll have the address of N
 
        my_loop:
            ; fscanf(myFile, format, &numberArray[i])
            push dword current
            push dword format
            push dword [file_descriptor]
            call [fscanf]
            add esp, 4 * 3
 
            ; if there was an error while reading
            cmp eax, -1
            je end_my_loop
 
            ; check the parity of the read number: eax = 1, if odd, eax = 0, otherwise
            push dword [current]
            call parity
 
            mov ebx, [current]
 
            cmp eax, 0
            je even_vector
            mov [edi], ebx
            add edi, 4
            jmp not_even_vector
            even_vector:
                mov [esi], ebx
                add esi, 4
            not_even_vector:
        jmp my_loop
 
        end_my_loop:
        ; close the file
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
 
        ; get the number of elements of P in ebx
        mov ebx, esi
        sub ebx, P
        mov ax, bx
        mov dx, 0
        mov bx, 4
        div bx
        cwde
        mov ebx, eax
        mov [len_P], ebx
 
        ; print message even numbers
        push dword even_message
        call [printf]
        add esp, 4 * 1
 
        ; the beginning of the P
        mov esi, P
        P_loop:
            push dword [esi]
            push dword format_print
            call [printf]
            add esp, 4 * 2
            add esi, 4
            dec ebx
            cmp ebx, 0
            jne P_loop
 
        ; clear the stack
        mov ecx, [len_P]
        pop_P_loop:
            add esp, 4
        loop pop_P_loop
 
        ; new line
        push dword new_line
        call [printf]
        add esp, 4 * 1
 
        ; print message odd numbers
        push dword odd_message
        call [printf]
        add esp, 4 * 1
 
        ; get the number of elements of N in ebx
        mov ebx, edi
        sub ebx, N
        mov ax, bx
        mov dx, 0
        mov bx, 4
        div bx
        cwde
        mov ebx, eax
        mov [len_N], ebx
 
        ; the beginning of the N
        mov edi, N
        N_loop:
            push dword [edi]
            push dword format_print
            call [printf]
            add esp, 4 * 2
            add edi, 4
            dec ebx
            cmp ebx, 0
            jne N_loop 
 
        ; clear the stack
        mov ecx, [len_N]
        pop_N_loop:
            add esp, 4
        loop pop_N_loop
 
        finish:
 
        push    dword 0   
        call    [exit]       
 