document.addEventListener("DOMContentLoaded", () => {
    const form = $("#form").get(0);

let selectedElement,
    gridTiles,
    allPieces,
    pieces;

// check if piece is in correct position
const checkPiece = (piece, parent) => {
  if($(piece).data("position") === $(parent).data("position")){
    $(piece).addClass("correct");
  } else if(piece.hasClass("correct")) {
    $(piece).removeClass("correct");
  }
}

// check if all pieces are in correct place
const checkWin = () => {
  if($(".correct").length === $(".piece").length){
    alert("You completed the puzzle! Well Done!");
  }
}

// once logic has run this gets the elements and shuffles the tiles
const puzzleInit = (pieces) => {
  gridTiles = $(".grid-tile");
  allPieces = $(".piece");
  pieceContainer = $(".pieces");

  allPieces.each(i => {
    let number = Math.floor(Math.random() * pieces);
    $(allPieces[number]).appendTo(pieceContainer);

    // user starts dragging event
    $(allPieces[i]).on("dragstart", function(event) {
      selectedElement = event.target.id;
    });
  });

  gridTiles.each(i => {
    // enable dropping
    $(gridTiles[i]).on("dragover", function(event) {
      event.preventDefault();
    });

    // user starts drop event
    $(gridTiles[i]).on("drop", event => {
      const selected = $("#" + selectedElement);
      const target = $(event.target).hasClass("piece")
      ? $(event.target).closest(".grid-tile")
      : "#" + event.target.id;

      // add piece to grid tile if tile empty or swap
      if($(target).html().length) {
        const pieceToSwap = $(target).find(".piece");
        const selectedParent = selected.parent();

        pieceToSwap.appendTo(selectedParent);
        selected.appendTo(target);

        checkPiece(selected, target);
        checkPiece(pieceToSwap, selectedParent);
      } else {
        selected.appendTo(target);
        checkPiece(selected, target);
      }

      checkWin();
    });
  });
}

// puzzle image piece template
const pieceTemplate = (num, img, pieceSize, posX, posY) => {
  return `
<div id="p${num}" class="piece" draggable=true data-position=${num} style="background-image: url(${img}); background-position: ${posX}px ${posY}px; width: ${pieceSize}px; height: ${pieceSize}px"></div>
`;
}

// puzzle drop grid tile template
const gridTemplate = (num, pieceSize) => {
  return `
<div id="g${num}" class="grid-tile" data-position=${num} style="width: ${pieceSize}px; height: ${pieceSize}px"></div>
`;
}

// creates puzzle from submitted form
const createPuzzle = (event) => {
  event.preventDefault();

  const piecesTarget = $("#pieces"),
        gridTarget = $("#grid").get(0),
        pieces = 16;

  // set image properties and get image
  const imgSize = 500,
        img = `https://picsum.photos/${imgSize}/${imgSize}`;

  // calculate row and piece sizes
  const rowSize = 500 / Math.sqrt(pieces),
        pieceSize = imgSize / Math.sqrt(pieces);

  // create empty game variables
  let puzzlePieces = '',
      gridPieces = '',
      posX = imgSize,
      posY = imgSize;

  // hide form
  form.classList.add("hidden");

  // create each piece as an element and calculate background position
  for(let i = 0; i < pieces; i++) {
    if(posX === 0) {
      posX = imgSize;
      posY -= pieceSize;
    }

    puzzlePieces += pieceTemplate(i, img, pieceSize, posX, posY);
    gridPieces += gridTemplate(i, pieceSize);
    posX -= pieceSize;
  }

  // insert templates into DOM
  $(piecesTarget).html(puzzlePieces);
  $(gridTarget).html(gridPieces);
  gridTarget.setAttribute("style", `width: ${imgSize}px; height: ${imgSize}px;`);

  puzzleInit(pieces);
}

form.addEventListener("submit", createPuzzle, true);
  });


