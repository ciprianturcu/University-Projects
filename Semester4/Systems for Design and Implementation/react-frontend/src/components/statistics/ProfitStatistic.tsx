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
} from "@mui/material";
import { useEffect, useState } from "react";
import { useResolvedPath } from "react-router-dom";
import { Profit } from "../../models/Profit";
import { BACKEND_API_URL } from "../../constants";

export const ProfitStatistic = () =>{
    const[loading, setLoading] = useState(true);
    const[profits, setProfits] = useState([]);

    useEffect(() => {
        fetch(`${BACKEND_API_URL}/profits/`)
            .then(response => response.json())
            .then(data => {
                setProfits(data);
                setLoading(false);
            })
    }, []);

    console.log(profits);

    return(
        <Container>
        <h1>Top 3 most profitable lawsuits</h1>
        {loading && <CircularProgress />}

        {!loading && profits.length == 0 && <div>No lawsuits found</div>}

        {!loading && profits.length > 0 && (
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 900 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>#</TableCell>
                            <TableCell align="center">Description</TableCell>
                            <TableCell align="center">Profit</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {profits.map((profits:Profit, index) => (
                            <TableRow key={index}>
                                <TableCell component="th" scope="row">
                                    {index + 1}
                                </TableCell>
                                <TableCell align="center">{profits.description}</TableCell>
                                <TableCell align="center">{profits.profit}</TableCell>
                            </TableRow>
                        ))}
                </TableBody>
                </Table>
            </TableContainer>
        )}
    </Container>
    )

}