export class Author {
	public id: number = 0;
	public firstName: string = '';
	public lastName: string = '';

	public book?: any;

	constructor(id: number, firstName: string, lastName: string) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}