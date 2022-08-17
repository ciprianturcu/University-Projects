bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

extern exit, fopen, fscanf, printf, fclose            
import exit msvcrt.dll
import fopen msvcrt.dll  
import fscanf msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll

%include "parity.asm"

; our data is declared here (the variables needed by our program)

; Read from file numbers.txt a string of numbers (odd and even). Build two strings using readen numbers:
; P – only with even numbers
; N – only with odd numbers
; Display the strings on the screen.

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
    lenP dd 0
    lenN dd 0
    P resd 10
    N resd 10
    
 
; our code starts here
segment code use32 class=code
    start:
        
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4 * 2
        
        ; check for error
        cmp eax, 0 
        je finish
        
        ;save file descriptor
        mov [file_descriptor],eax
        
        mov esi,P
        mov edi,N
        
        main_loop:
            ;reading number
            push dword current
            push dword format
            push dword [file_descriptor]
            call [fscanf]
            add esp, 4*3
            
            ;check for error
            cmp eax,-1
            je end_main_loop
            
            push dword [current]
            call parity
            
            mov ebx,[current]
            
            cmp eax,0
            je even_string
            mov [edi],ebx
            add edi, 4
            jmp odd_string
            even_string:
                mov [esi],ebx
                add esi, 4
            odd_string:
        jmp main_loop
        
        end_main_loop:
        
        ;close the file
        push dword [file_descriptor]
        call [fclose]
        add esp,4
        
        ; get length of elements in P
        mov ebx, esi
        sub ebx,P
        mov ax, bx
        mov dx,0
        mov bx,4
        div bx
        cwde
        mov ebx, eax
        mov [lenP], ebx
        
        ;msj even string
        push dword even_message
        call [printf]
        add esp,4*1
        
        mov esi, P
        Ploop:
            push dword [esi]
            push dword format_print
            call [printf]
            add esp, 4*2
            add esi, 4
            dec ebx
            cmp ebx, 0
            jne Ploop
        
         ;clear the stack
        mov ecx, [lenP]
        pop_P_loop:
            add esp, 4
        loop pop_P_loop
        
        ; new line
        push dword new_line
        call [printf]
        add esp, 4 * 1
        
        ;msj odd string
        push dword odd_message
        call [printf]
        add esp,4*1
        
        ; get the number of elements of N in ebx
        mov ebx, edi
        sub ebx, N
        mov ax, bx
        mov dx, 0
        mov bx, 4
        div bx
        cwde
        mov ebx, eax
        mov [lenN], ebx
        
        mov edi, N
        Nloop:
            push dword [edi]
            push dword format_print
            call [printf]
            add esp, 4*2
            add edi, 4
            dec ebx
            cmp ebx, 0
            jne Nloop
            
        ;clear the stack
        mov ecx, [lenN]
        pop_N_loop:
            add esp, 4
        loop pop_N_loop
        
        finish :
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
