import { Backdrop, Button, Card, CardActions, CardContent, IconButton, InputLabel, MenuItem, Select, TextField } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { BACKEND_API_URL } from "../../constants";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { Attorney, ExperienceType, SpecializationType } from "../../models/Attorney";

export const AttorneyUpdate = () => {
	const navigate = useNavigate();
	const { attorneyId } = useParams();
	const [loading, setLoading] = useState(true);
	
	const [attorney, setAttorney] = useState({
		name: "",
		specialization: SpecializationType['CIVIL'],
		date_of_birth:"",
		experience: ExperienceType['MID'],
        city:"",
		fee:0,
	});

	useEffect(() => {
		const fetchAttorney = async () => {
			const response = await fetch(`${BACKEND_API_URL}/attorney/${attorneyId}/`);
			const attorney = await response.json();
			setAttorney({
				name: attorney.name,
				specialization: attorney.specialization,
				date_of_birth: attorney.date_of_birth,
				experience: attorney.experience,
				city: attorney.city,
                fee: attorney.fee,
			});
			setLoading(false);
			console.log(attorney);
		};
		fetchAttorney();
	}, [attorneyId]);

	const updateAttorney = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try{
			await axios.put(`${BACKEND_API_URL}/attorney/${attorneyId}/`, attorney);
			navigate(`/attorney/${attorneyId}/details`)
		}catch(error)
		{
			console.log(error);
		}
	}


	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/attorney`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={updateAttorney}>
						<TextField
							id="name"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{mb : 2}}
							onChange={(event) => setAttorney({ ...attorney, name: event.target.value})}	
						/>
						<InputLabel id="attorney-specialization-label">Specialization</InputLabel>
						<Select
							labelId="attorney-specialization-label"
							id="attorney-specialization"
							value={attorney.specialization}
							onChange={(event) => setAttorney({...attorney, specialization: event.target.value as SpecializationType})}
						>
							<MenuItem value="Civil">Civil</MenuItem>
							<MenuItem value="Commercial">Commercial</MenuItem>
                            <MenuItem value="Criminal">Criminal</MenuItem>
                            <MenuItem value="Family">Family</MenuItem>
                            <MenuItem value="Juvenile">Juvenile</MenuItem>
                            <MenuItem value="Tax">Tax</MenuItem>
						</Select>
						<TextField
							id="date_of_birth"
							label="Birthday"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAttorney({ ...attorney, date_of_birth: event.target.value })}
						/>
						<InputLabel id="attorney-experience-label">Specialization</InputLabel>
						<Select
							labelId="attorney-experience-label"
							id="attorney-experience"
							value={attorney.experience}
							onChange={(event) => setAttorney({...attorney, experience: event.target.value as ExperienceType})}
						>
							<MenuItem value="Junior">Junior</MenuItem>
							<MenuItem value="Mid">Mid</MenuItem>
                            <MenuItem value="Senior">Senior</MenuItem>
						</Select>
                        <TextField
							id="city"
							label="City"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAttorney({ ...attorney, city: event.target.value })}
						/>
                        <TextField
							id="fee"
							label="Fee"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setAttorney({ ...attorney, fee: event.target.value as unknown as number})}
						/>
						<Button type="submit">Update Attorney</Button>
					</form>
				</CardContent>
				<CardActions>
				</CardActions>
			</Card>
		</Container>
	);
};
