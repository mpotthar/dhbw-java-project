/**
 * Global variables to keep track of whether a Sudoku puzzle has been generated/revealed.
 */
let sudokuGenerated = false;
let sudokuRevealed = false;

/**
 * Generates a Sudoku puzzle by making a request to the server API.
 * Populates the Sudoku puzzle with the generated data.
 * @returns {Promise<void>} A promise that resolves when the Sudoku puzzle is generated and populated.
 */
async function generateSudoku() {
    try {
        let url = "http://" + window.location.host + "/api/generate/"
        let response = await fetch(url);
        let sudokuData = await response.json();
        populateSudoku(sudokuData);
        sudokuGenerated = true;
        sudokuRevealed = false;
    } catch (error) {
        console.error('Error generating Sudoku:', error);
    }
}

/**
 * Populates the Sudoku table with data.
 * 
 * @param {Array<Array<{isHidden: boolean, value: string}>>} sudokuData - The Sudoku data to populate the table with.
 */
function populateSudoku(sudokuData) {
    let table = document.getElementById('sudoku-table');
    table.innerHTML = ''; // Clear any existing table

    for (let row = 0; row < 9; row++) {
        let tr = document.createElement('tr');
        for (let col = 0; col < 9; col++) {
            let td = document.createElement('td');
            let input = document.createElement('input');
            input.type = 'text';
            input.maxLength = 1;

            if (sudokuData[row][col].isHidden) {
                input.value = '';
                input.dataset.solution = sudokuData[row][col].value;
            } else {
                input.value = sudokuData[row][col].value;
                input.readOnly = true;
            }

            td.appendChild(input);
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }

    // Add style to alternate the thickness of the lines
    let rows = table.getElementsByTagName('tr');
    for (let i = 0; i < rows.length; i++) {
        if (i % 3 === 2) {
            rows[i].style.borderBottomWidth = '3px';
        } else {
            rows[i].style.borderBottomWidth = '1px';
        }
        if (i === 0 || i === rows.length - 1) {
            rows[i].style.borderTopWidth = '3px';
        }
    }

    let cells = table.getElementsByTagName('td');
    for (let i = 0; i < cells.length; i++) {
        if ((i + 1) % 3 === 0) {
            cells[i].style.borderRightWidth = '3px';
        } else {
            cells[i].style.borderRightWidth = '1px';
        }
        if (i % 9 === 0) {
            cells[i].style.borderLeftWidth = '3px';
        }
    }

    // Add style to alternate the thickness of the horizontal lines
    for (let i = 0; i < rows.length; i++) {
        if ((i + 1) % 3 === 0) {
            let tds = rows[i].getElementsByTagName('td');
            for (let j = 0; j < tds.length; j++) {
                tds[j].style.borderBottomWidth = '3px';
            }
        }
    }

    // Add style to make the top outer line bold
    rows[0].style.borderTopWidth = '3px';

    let cellsInFirstRow = rows[0].getElementsByTagName('td');
    for (let i = 0; i < cellsInFirstRow.length; i++) {
        cellsInFirstRow[i].style.borderTopWidth = '3px';
    }

    // Center the Sudoku table
    table.style.margin = '0 auto';

    // Add event listeners to highlight the row and column of the clicked/focused entry
    let inputs = table.getElementsByTagName('input');
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].addEventListener('focus', function() {
            if (!sudokuRevealed) {
                let row = Math.floor(i / 9);
                let col = i % 9;
                highlightRowAndColumn(row, col);
            }
        });
    }

    // Call the function to enable arrow navigation
    addArrowKeyNavigation();
}

/**
 * Highlights the row and column of the active entry.
 * 
 * @param {number} row - The row index of the active entry.
 * @param {number} col - The column index of the active entry.
 */
function highlightRowAndColumn(row, col) {
    let table = document.getElementById('sudoku-table');
    let rows = table.getElementsByTagName('tr');
    let cells = table.getElementsByTagName('td');
    let inputs = table.getElementsByTagName('input');

    // Remove any existing highlighting
    for (let i = 0; i < rows.length; i++) {
        rows[i].style.backgroundColor = '';
    }
    for (let i = 0; i < cells.length; i++) {
        cells[i].style.backgroundColor = '';
    }
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].style.backgroundColor = '';
    }

    // Highlight the 3x3 block
    let blockRow = Math.floor(row / 3) * 3;
    let blockCol = Math.floor(col / 3) * 3;
    for (let i = blockRow; i < blockRow + 3; i++) {
        for (let j = blockCol; j < blockCol + 3; j++) {
            let cellIndex = i * 9 + j;
            cells[cellIndex].style.backgroundColor = '#EEEEEE';
            inputs[cellIndex].style.backgroundColor = '#EEEEEE';
        }
    }

    // Highlight the row
    for (let i = 0; i < 9; i++) {
        let cellIndex = row * 9 + i;
        cells[cellIndex].style.backgroundColor = '#D3D3D3';
        inputs[cellIndex].style.backgroundColor = '#D3D3D3';
    }

    // Highlight the column
    for (let i = 0; i < 9; i++) {
        let cellIndex = i * 9 + col;
        cells[cellIndex].style.backgroundColor = '#D3D3D3';
        inputs[cellIndex].style.backgroundColor = '#D3D3D3';
    }
}



/**
 * Reveals the solution values in the Sudoku table inputs.
 * Checks if the current inputs in the Sudoku table are correct.
 * Marks incorrect/missing fields in red and correct fields in green.
 */
function revealValues() {
    let inputs = document.querySelectorAll('#sudoku-table input');
    if (!sudokuGenerated) {
        alert('No Sudoku has been generated yet.\nPlease generate a Sudoku first.');
        return;
    } else if (sudokuRevealed) {
        alert('The solution has already been revealed.');
        return;
    }
    inputs.forEach(input => {
        input.style.backgroundColor = ''; // Reset the background color
        if (input.dataset.solution) {
            if (input.value == input.dataset.solution) {
                input.style.backgroundColor = '#DAF7A6';
            } else {
                input.style.backgroundColor = '#FF5733';
                input.value = input.dataset.solution;
            }
            input.readOnly = true; // Set all fields to readonly
        } 
    });
    sudokuRevealed = true;
}


/**
 * Adds arrow key navigation to the Sudoku table inputs.
 */
function addArrowKeyNavigation() {
    let inputs = document.querySelectorAll('#sudoku-table input');
    inputs.forEach((input, index) => {
        input.addEventListener('keydown', function(event) {
            let row = Math.floor(index / 9);
            let col = index % 9;
            let newRow = row;
            let newCol = col;

            switch (event.key) {
                case 'ArrowUp':
                    newRow = row - 1;
                    break;
                case 'ArrowDown':
                    newRow = row + 1;
                    break;
                case 'ArrowLeft':
                    newCol = col - 1;
                    break;
                case 'ArrowRight':
                    newCol = col + 1;
                    break;
                default:
                    return; // If it's not an arrow key, ignore the event
            }

            // Check if the new position is within the boundaries
            if (newRow >= 0 && newRow < 9 && newCol >= 0 && newCol < 9) {
                inputs[newRow * 9 + newCol].focus();
            }

            event.preventDefault(); // Prevent the default behavior of arrow keys
        });
    });
}

