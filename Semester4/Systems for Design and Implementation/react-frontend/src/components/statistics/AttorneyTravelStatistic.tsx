import {
	TableContainer,
	Paper,
	Table,
	TableHead,
	TableRow,
	TableCell,
	TableBody,
	CircularProgress,
	Container,
	IconButton,
	Tooltip,
    Button,
} from "@mui/material";
import { useEffect, useState } from "react";
import { BACKEND_API_URL } from "../../constants";
import { AttorneyTravel } from "../../models/AttorneyTravel";
import { trace } from "console";

export const AttorneyTravelStatistic = () =>{
    const[loading, setLoading] = useState(true);
    const[travels, setTravels] = useState([]);
    const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(10);
	const current = (page - 1) * pageSize + 1;

    // useEffect(() => {
    //     fetch(`${BACKEND_API_URL}/travel_sheet/`)
    //         .then(response => response.json())
    //         .then(data => {
    //             setTravels(data);
    //             setLoading(false);
    //         })
    // }, []);

    // console.log(travels);

    const fetchAttorneyTravel = async() => {
		setLoading(true);
		const response = await fetch(
			`${BACKEND_API_URL}/travel_sheet/?page=${page}&page_size=${pageSize}`
		);
		const {count, next, previous, results} = await response.json();
		setTravels(results);
		setLoading(false);
	};

	useEffect(() => {
		fetchAttorneyTravel();
	}, [page]);

	console.log(travels);

    return(
        <Container>
        <h1>Attorneys that need to travel for a lawsuit</h1>
        {loading && <CircularProgress />}

        {!loading && travels.length == 0 && <div>No attorneys that need to travel were found</div>}

        {!loading && travels.length > 0 && (
            <><TableContainer component={Paper}>
                    <Table sx={{ minWidth: 900 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>#</TableCell>
                                <TableCell align="center">Name</TableCell>
                                <TableCell align="center">City of attorney</TableCell>
                                <TableCell align="center">Lawsuit state</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {travels.map((travels: AttorneyTravel, index) => (
                                <TableRow key={index}>
                                    <TableCell component="th" scope="row">
                                        {index + 1}
                                    </TableCell>
                                    <TableCell align="center">{travels.name}</TableCell>
                                    <TableCell align="center">{travels.city}</TableCell>
                                    <TableCell align="center">{travels.lawsuit_state}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
                <Button disabled={page === 1} onClick={() => setPage(page - 1)}>Previous</Button>
                <Button disabled={travels.length < pageSize} onClick={() => setPage(page + 1)}>Next</Button>
                </>
            
        )}
    </Container>
    )

}