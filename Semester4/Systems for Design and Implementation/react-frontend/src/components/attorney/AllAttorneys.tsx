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
import { Link } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { Attorney } from "../../models/Attorney";

export const AllAttorneys = () => {
	const [loading, setLoading] = useState(false);
	const [attorneys, setAttorneys] = useState<Attorney[]>([]);
	const [page, setPage] = useState(1);
	const [pageSize, setPageSize] = useState(10);
	const current = (page - 1) * pageSize + 1;

	// useEffect(() => {
	// 	setLoading(true);
	// 	fetch(`${BACKEND_API_URL}/attorney/`)
	// 		.then((response) => response.json())
	// 		.then((data) => {
	// 			setAttorneys(data);
	// 			setLoading(false);
	// 		});
	// }, []);

	const fetchAttorneys = async() => {
		setLoading(true);
		const response = await fetch(
			`${BACKEND_API_URL}/attorney/?page=${page}&page_size=${pageSize}`
		);
		const {count, next, previous, results} = await response.json();
		setAttorneys(results);
		setLoading(false);
	};

	useEffect(() => {
		fetchAttorneys();
	}, [page]);

	console.log(attorneys)

	return (
		<Container>
			<h1>All Attorneys</h1>

			{loading && <CircularProgress />}
			{!loading && attorneys.length === 0 && <p>No attorneys found</p>}
			{!loading && (
				<IconButton component={Link} sx={{ mr: 3 }} to={`/attorney/add`}>
					<Tooltip title="Add a new attorney" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
			)}

			{!loading && attorneys.length > 0 && (
				<><TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="right">Name</TableCell>
								<TableCell align="right">Phone Number</TableCell>
								<TableCell align="right">City</TableCell>
								<TableCell align="center">Birthday</TableCell>
								<TableCell align="center">Type</TableCell>
								<TableCell align="center">Fee</TableCell>
								<TableCell align="center">Nr. ongoing lawsuits</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{attorneys.map((attorney, index) => (
								<TableRow key={attorney.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/attorney/${attorney.id}/details`} title="View attorney details">
											{attorney.name}
										</Link>
									</TableCell>
									<TableCell align="right">{attorney.specialization}</TableCell>
									<TableCell align="right">{attorney.date_of_birth}</TableCell>
									<TableCell align="right">{attorney.experience}</TableCell>
									<TableCell align="right">{attorney.city}</TableCell>
									<TableCell align="right">{attorney.fee}</TableCell>
									<TableCell align="right">{attorney.nb_lawsuits}</TableCell>
									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/attorney/${attorney.id}/details`}>
											<Tooltip title="View attorney details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/attorney/${attorney.id}/edit`}>
											<EditIcon />
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/attorney/${attorney.id}/delete`}>
											<DeleteForeverIcon sx={{ color: "red" }} />
										</IconButton>
									</TableCell>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
				<Button disabled={page === 1} onClick={() => setPage(page - 1)}>Previous</Button>
				<Button disabled={attorneys.length < pageSize} onClick={() => setPage(page + 1)}>Next</Button></>
				
			)}
		</Container>
	);
};